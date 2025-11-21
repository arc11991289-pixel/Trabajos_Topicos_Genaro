<?xml version="1.0" encoding="UTF-8" ?>

<Form version="1.3" maxVersion="1.9" type="org.netbeans.modules.form.forminfo.JFrameFormInfo">
  <Properties>
    <Property name="defaultCloseOperation" type="int" value="3"/>
  </Properties>
  <AuxValues>
    <AuxValue name="FormSettings_autoResourcing" type="java.lang.Integer" value="0"/>
    <AuxValue name="FormSettings_autoSetComponentName" type="java.lang.Boolean" value="false"/>
    <AuxValue name="FormSettings_generateFQN" type="java.lang.Boolean" value="true"/>
    <AuxValue name="FormSettings_generateMnemonicsCode" type="java.lang.Boolean" value="false"/>
    <AuxValue name="FormSettings_i18nAutoMode" type="java.lang.Boolean" value="false"/>
    <AuxValue name="FormSettings_layoutCodeTarget" type="java.lang.Integer" value="1"/>
    <AuxValue name="FormSettings_listenerGenerationStyle" type="java.lang.Integer" value="1"/>
    <AuxValue name="FormSettings_variablesLocal" type="java.lang.Boolean" value="false"/>
    <AuxValue name="FormSettings_variablesModifier" type="java.lang.Integer" value="2"/>
  </AuxValues>
  <SyntheticProperties>
    <SyntheticProperty name="formSizePolicy" type="int" value="1"/>
    <SyntheticProperty name="generateCenter" type="boolean" value="false"/>
  </SyntheticProperties>

  <Layout>
    <DimensionLayout dim="0">
      <Group type="103" groupAlignment="0" attributes="0">
        <Group type="102" attributes="0">
          <Group type="103" groupAlignment="0" attributes="0">
            <Group type="102" alignment="0" attributes="0">
              <EmptySpace min="-2" pref="55" max="-2" attributes="0"/>
              <Group type="103" groupAlignment="0" max="-2" attributes="0">
                <Group type="102" attributes="0">
                  <Group type="103" groupAlignment="1" attributes="0">
                    <Component id="labelMaximo" min="-2" max="-2" attributes="0"/>
                    <Component id="labelNumero" min="-2" max="-2" attributes="0"/>
                  </Group>
                  <EmptySpace min="25" pref="25" max="-2" attributes="0"/>
                  <Group type="103" groupAlignment="0" attributes="0">
                    <Component id="spinnerMax" max="32767" attributes="0"/>
                    <Group type="102" attributes="0">
                      <Component id="fieldResultado" min="-2" pref="105" max="-2" attributes="0"/>
                      <EmptySpace min="0" pref="0" max="32767" attributes="0"/>
                    </Group>
                  </Group>
                </Group>
                <Group type="102" alignment="0" attributes="0">
                  <Component id="labelMinimo" min="-2" max="-2" attributes="0"/>
                  <EmptySpace min="-2" pref="45" max="-2" attributes="0"/>
                  <Component id="spinnerMin" min="-2" pref="125" max="-2" attributes="0"/>
                </Group>
              </Group>
            </Group>
            <Group type="102" alignment="0" attributes="0">
              <EmptySpace min="-2" pref="125" max="-2" attributes="0"/>
              <Component id="btnGenerar" min="-2" max="-2" attributes="0"/>
            </Group>
          </Group>
          <EmptySpace pref="65" max="32767" attributes="0"/>
        </Group>
      </Group>
    </DimensionLayout>
    <DimensionLayout dim="1">
      <Group type="103" groupAlignment="0" attributes="0">
        <Group type="102" alignment="0" attributes="0">
          <EmptySpace min="-2" pref="65" max="-2" attributes="0"/>
          <Group type="103" groupAlignment="3" attributes="0">
            <Component id="labelMinimo" alignment="3" min="-2" max="-2" attributes="0"/>
            <Component id="spinnerMin" alignment="3" min="-2" max="-2" attributes="0"/>
          </Group>
          <EmptySpace min="-2" pref="35" max="-2" attributes="0"/>
          <Group type="103" groupAlignment="3" attributes="0">
            <Component id="spinnerMax" alignment="3" min="-2" max="-2" attributes="0"/>
            <Component id="labelMaximo" alignment="3" min="-2" max="-2" attributes="0"/>
          </Group>
          <EmptySpace min="-2" pref="30" max="-2" attributes="0"/>
          <Group type="103" groupAlignment="3" attributes="0">
            <Component id="labelNumero" alignment="3" min="-2" max="-2" attributes="0"/>
            <Component id="fieldResultado" alignment="3" min="-2" max="-2" attributes="0"/>
          </Group>
          <EmptySpace min="-2" pref="50" max="-2" attributes="0"/>
          <Component id="btnGenerar" min="-2" max="-2" attributes="0"/>
          <EmptySpace pref="35" max="32767" attributes="0"/>
        </Group>
      </Group>
    </DimensionLayout>
  </Layout>
  <SubComponents>
    <Component class="javax.swing.JLabel" name="labelMinimo">
      <Properties>
        <Property name="text" type="java.lang.String" value="Valor Inicial"/>
      </Properties>
    </Component>
    <Component class="javax.swing.JLabel" name="labelMaximo">
      <Properties>
        <Property name="text" type="java.lang.String" value="Valor Final"/>
      </Properties>
    </Component>
    <Component class="javax.swing.JLabel" name="labelNumero">
      <Properties>
        <Property name="text" type="java.lang.String" value="Resultado"/>
      </Properties>
    </Component>
    <Component class="javax.swing.JSpinner" name="spinnerMin">
      <Events>
        <EventHandler event="stateChanged" listener="javax.swing.event.ChangeListener" parameters="javax.swing.event.ChangeEvent" handler="spinnerMinStateChanged"/>
      </Events>
    </Component>
    <Component class="javax.swing.JSpinner" name="spinnerMax">
      <Events>
        <EventHandler event="stateChanged" listener="javax.swing.event.ChangeListener" parameters="javax.swing.event.ChangeEvent" handler="spinnerMaxStateChanged"/>
      </Events>
    </Component>
    <Component class="javax.swing.JTextField" name="fieldResultado">
      <Properties>
        <Property name="editable" type="boolean" value="false"/>
        <Property name="horizontalAlignment" type="int" value="10"/>
        <Property name="text" type="java.lang.String" value="N/A"/>
      </Properties>
    </Component>
    <Component class="javax.swing.JButton" name="btnGenerar">
      <Properties>
        <Property name="text" type="java.lang.String" value="Aceptar"/>
      </Properties>
      <Events>
        <EventHandler event="actionPerformed" listener="java.awt.event.ActionListener" parameters="java.awt.event.ActionEvent" handler="btnGenerarActionPerformed"/>
      </Events>
    </Component>
  </SubComponents>
</Form>