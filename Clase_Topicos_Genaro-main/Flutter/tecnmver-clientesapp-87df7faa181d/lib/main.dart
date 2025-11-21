import 'dart:io';
import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:image_picker/image_picker.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:geolocator/geolocator.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Directorio de Clientes',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const HomePage(),
    );
  }
}

// ---------------------------------------------------------
// MODELO DE DATOS
// ---------------------------------------------------------
class Client {
  final int? id;
  final String name;
  final String address;
  final String city;
  final String state;
  final String zipCode;
  final String phone;
  final String email;
  final String? photoPath;
  final double latitude;
  final double longitude;

  Client({
    this.id,
    required this.name,
    required this.address,
    required this.city,
    required this.state,
    required this.zipCode,
    required this.phone,
    required this.email,
    this.photoPath,
    required this.latitude,
    required this.longitude,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'address': address,
      'city': city,
      'state': state,
      'zipCode': zipCode,
      'phone': phone,
      'email': email,
      'photoPath': photoPath,
      'latitude': latitude,
      'longitude': longitude,
    };
  }

  static Client fromMap(Map<String, dynamic> map) {
    return Client(
      id: map['id'],
      name: map['name'],
      address: map['address'],
      city: map['city'],
      state: map['state'],
      zipCode: map['zipCode'],
      phone: map['phone'],
      email: map['email'],
      photoPath: map['photoPath'],
      latitude: map['latitude'],
      longitude: map['longitude'],
    );
  }
}

// ---------------------------------------------------------
// BASE DE DATOS HELPER
// ---------------------------------------------------------
class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._init();
  static Database? _database;

  DatabaseHelper._init();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDB('clients.db');
    return _database!;
  }

  Future<Database> _initDB(String filePath) async {
    final dbPath = await getDatabasesPath();
    final path = join(dbPath, filePath);

    return await openDatabase(path, version: 1, onCreate: _createDB);
  }

  Future _createDB(Database db, int version) async {
    await db.execute('''
    CREATE TABLE clients (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT,
      address TEXT,
      city TEXT,
      state TEXT,
      zipCode TEXT,
      phone TEXT,
      email TEXT,
      photoPath TEXT,
      latitude REAL,
      longitude REAL
    )
    ''');
  }

  Future<int> create(Client client) async {
    final db = await instance.database;
    return await db.insert('clients', client.toMap());
  }

  Future<Client?> read(int id) async {
    final db = await instance.database;
    final maps = await db.query(
      'clients',
      columns: null,
      where: 'id = ?',
      whereArgs: [id],
    );

    if (maps.isNotEmpty) {
      return Client.fromMap(maps.first);
    } else {
      return null;
    }
  }

  Future<List<Client>> readAll() async {
    final db = await instance.database;
    final result = await db.query('clients');
    return result.map((json) => Client.fromMap(json)).toList();
  }

  Future<int> update(Client client) async {
    final db = await instance.database;
    return db.update(
      'clients',
      client.toMap(),
      where: 'id = ?',
      whereArgs: [client.id],
    );
  }

  Future<int> delete(int id) async {
    final db = await instance.database;
    return await db.delete('clients', where: 'id = ?', whereArgs: [id]);
  }
}

// ---------------------------------------------------------
// PANTALLA PRINCIPAL (LISTA)
// ---------------------------------------------------------
class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late Future<List<Client>> clients;

  @override
  void initState() {
    super.initState();
    refreshClients();
  }

  Future refreshClients() async {
    setState(() {
      clients = DatabaseHelper.instance.readAll();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Directorio de Clientes')),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () async {
          await Navigator.of(context).push(
            MaterialPageRoute(builder: (context) => const ClientFormPage()),
          );
          refreshClients();
        },
      ),
      body: FutureBuilder<List<Client>>(
        future: clients,
        builder: (context, snapshot) {
          if (!snapshot.hasData)
            return const Center(child: CircularProgressIndicator());
          if (snapshot.data!.isEmpty)
            return const Center(child: Text("No hay clientes"));

          return ListView.builder(
            itemCount: snapshot.data!.length,
            itemBuilder: (context, index) {
              Client client = snapshot.data![index];
              return ListTile(
                leading: CircleAvatar(
                  backgroundImage: client.photoPath != null
                      ? FileImage(File(client.photoPath!))
                      : null,
                  child: client.photoPath == null
                      ? const Icon(Icons.person)
                      : null,
                ),
                title: Text(client.name),
                subtitle: Text("${client.city}, ${client.state}"),
                onTap: () async {
                  await Navigator.of(context).push(
                    MaterialPageRoute(
                      builder: (context) => ClientDetailPage(client: client),
                    ),
                  );
                  refreshClients();
                },
              );
            },
          );
        },
      ),
    );
  }
}

// ---------------------------------------------------------
// PANTALLA DE FORMULARIO (AGREGAR/EDITAR)
// ---------------------------------------------------------
class ClientFormPage extends StatefulWidget {
  final Client? client;
  const ClientFormPage({super.key, this.client});

  @override
  State<ClientFormPage> createState() => _ClientFormPageState();
}

class _ClientFormPageState extends State<ClientFormPage> {
  final _formKey = GlobalKey<FormState>();

  // Controllers
  final _nameCtrl = TextEditingController();
  final _addressCtrl = TextEditingController();
  final _cityCtrl = TextEditingController();
  final _stateCtrl = TextEditingController();
  final _zipCtrl = TextEditingController();
  final _phoneCtrl = TextEditingController();
  final _emailCtrl = TextEditingController();

  String? _photoPath;
  double? _latitude;
  double? _longitude;

  @override
  void initState() {
    super.initState();
    if (widget.client != null) {
      _nameCtrl.text = widget.client!.name;
      _addressCtrl.text = widget.client!.address;
      _cityCtrl.text = widget.client!.city;
      _stateCtrl.text = widget.client!.state;
      _zipCtrl.text = widget.client!.zipCode;
      _phoneCtrl.text = widget.client!.phone;
      _emailCtrl.text = widget.client!.email;
      _photoPath = widget.client!.photoPath;
      _latitude = widget.client!.latitude;
      _longitude = widget.client!.longitude;
    }
  }

  Future _pickImage() async {
    final pickedFile = await ImagePicker().pickImage(
      source: ImageSource.gallery,
    );
    if (pickedFile != null) {
      setState(() => _photoPath = pickedFile.path);
    }
  }

  Future _getCurrentLocation() async {
    // Nota: En producción debes manejar permisos detalladamente
    LocationPermission permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
    }

    if (permission == LocationPermission.whileInUse ||
        permission == LocationPermission.always) {
      Position position = await Geolocator.getCurrentPosition(
        desiredAccuracy: LocationAccuracy.high,
      );
      setState(() {
        _latitude = position.latitude;
        _longitude = position.longitude;
      });
      ScaffoldMessenger.of(
        this.context,
      ).showSnackBar(const SnackBar(content: Text("Ubicación actualizada")));
    }
  }

  Future _saveClient() async {
    if (_formKey.currentState!.validate() && _latitude != null) {
      final client = Client(
        id: widget.client?.id,
        name: _nameCtrl.text,
        address: _addressCtrl.text,
        city: _cityCtrl.text,
        state: _stateCtrl.text,
        zipCode: _zipCtrl.text,
        phone: _phoneCtrl.text,
        email: _emailCtrl.text,
        photoPath: _photoPath,
        latitude: _latitude!,
        longitude: _longitude!,
      );

      if (widget.client == null) {
        await DatabaseHelper.instance.create(client);
      } else {
        await DatabaseHelper.instance.update(client);
      }
      if (mounted) Navigator.of(this.context).pop();
    } else if (_latitude == null) {
      ScaffoldMessenger.of(this.context).showSnackBar(
        const SnackBar(content: Text("Debes obtener la ubicación")),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.client == null ? 'Nuevo Cliente' : 'Editar Cliente'),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              GestureDetector(
                onTap: _pickImage,
                child: CircleAvatar(
                  radius: 50,
                  backgroundImage: _photoPath != null
                      ? FileImage(File(_photoPath!))
                      : null,
                  child: _photoPath == null
                      ? const Icon(Icons.add_a_photo, size: 40)
                      : null,
                ),
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _nameCtrl,
                decoration: const InputDecoration(labelText: 'Nombre'),
                validator: (v) => v!.isEmpty ? 'Requerido' : null,
              ),
              TextFormField(
                controller: _addressCtrl,
                decoration: const InputDecoration(labelText: 'Dirección'),
              ),
              Row(
                children: [
                  Expanded(
                    child: TextFormField(
                      controller: _cityCtrl,
                      decoration: const InputDecoration(labelText: 'Ciudad'),
                    ),
                  ),
                  const SizedBox(width: 10),
                  Expanded(
                    child: TextFormField(
                      controller: _stateCtrl,
                      decoration: const InputDecoration(labelText: 'Estado'),
                    ),
                  ),
                ],
              ),
              TextFormField(
                controller: _zipCtrl,
                decoration: const InputDecoration(labelText: 'Código Postal'),
                keyboardType: TextInputType.number,
              ),
              TextFormField(
                controller: _phoneCtrl,
                decoration: const InputDecoration(labelText: 'Teléfono'),
                keyboardType: TextInputType.phone,
              ),
              TextFormField(
                controller: _emailCtrl,
                decoration: const InputDecoration(labelText: 'Correo'),
                keyboardType: TextInputType.emailAddress,
              ),
              const SizedBox(height: 20),
              ElevatedButton.icon(
                onPressed: _getCurrentLocation,
                icon: const Icon(Icons.location_on),
                label: Text(
                  _latitude == null
                      ? "Capturar Ubicación Actual"
                      : "Ubicación: $_latitude, $_longitude",
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: _saveClient,
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(double.infinity, 50),
                ),
                child: const Text('Guardar'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

// ---------------------------------------------------------
// PANTALLA DE DETALLE (ACCIONES Y MAPA)
// ---------------------------------------------------------
class ClientDetailPage extends StatelessWidget {
  final Client client;

  const ClientDetailPage({super.key, required this.client});

  Future<void> _makePhoneCall(String phoneNumber) async {
    final Uri launchUri = Uri(scheme: 'tel', path: phoneNumber);
    if (await canLaunchUrl(launchUri)) {
      await launchUrl(launchUri);
    }
  }

  Future<void> _sendEmail(String email) async {
    final Uri launchUri = Uri(scheme: 'mailto', path: email);
    if (await canLaunchUrl(launchUri)) {
      await launchUrl(launchUri);
    }
  }

  Future<void> _deleteClient(BuildContext context) async {
    await DatabaseHelper.instance.delete(client.id!);
    if (context.mounted) Navigator.of(context).pop();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(client.name),
        actions: [
          IconButton(
            icon: const Icon(Icons.edit),
            onPressed: () {
              Navigator.of(context).pushReplacement(
                MaterialPageRoute(
                  builder: (context) => ClientFormPage(client: client),
                ),
              );
            },
          ),
          IconButton(
            icon: const Icon(Icons.delete),
            onPressed: () => _deleteClient(context),
          ),
        ],
      ),
      body: Column(
        children: [
          if (client.photoPath != null)
            Image.file(
              File(client.photoPath!),
              height: 200,
              width: double.infinity,
              fit: BoxFit.cover,
            ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    ElevatedButton.icon(
                      onPressed: () => _makePhoneCall(client.phone),
                      icon: const Icon(Icons.call),
                      label: const Text("Llamar"),
                    ),
                    ElevatedButton.icon(
                      onPressed: () => _sendEmail(client.email),
                      icon: const Icon(Icons.email),
                      label: const Text("Correo"),
                    ),
                  ],
                ),
                const SizedBox(height: 20),
                Text(
                  "${client.address}, ${client.city}",
                  style: const TextStyle(fontSize: 18),
                ),
                const SizedBox(height: 10),
                SizedBox(
                  height: 250,
                  width: double.infinity,
                  child: GoogleMap(
                    initialCameraPosition: CameraPosition(
                      target: LatLng(client.latitude, client.longitude),
                      zoom: 15,
                    ),
                    markers: {
                      Marker(
                        markerId: const MarkerId('clientLoc'),
                        position: LatLng(client.latitude, client.longitude),
                        infoWindow: InfoWindow(title: client.name),
                      ),
                    },
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
