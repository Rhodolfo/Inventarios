package org.arena.JumexHerramienta

import scalafx.application._
import scalafx.application.JFXApp
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.layout._
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.geometry.Pos
import scalafx.geometry.Insets
import scalafx.event.ActionEvent
import scalafx.stage.FileChooser
import scalafx.scene.control.{Menu, MenuItem, SeparatorMenuItem, ComboBox}
import org.arena.JumexHerramienta.Parametros._

// Duplicate child added - onAction location 
//
object  Inventarios  extends JFXApp{
    
     stage = new JFXApp.PrimaryStage{
      title = "Simulador de Inventarios"
      scene = new Scene(400,350){
         
      val menuBar = new MenuBar  //
      val fileMenu = new Menu("Archivo") // Dropdown menu name
      val exitItem = new MenuItem("Cerrar") // Dropdown menu Exit item - Exits app when clicked

      fileMenu.items = List(new SeparatorMenuItem, exitItem) 
      menuBar.menus = List(fileMenu)

      exitItem.onAction = (ae: ActionEvent) => {
        stage.close()
      }
        
      val botonCatalogo = new Button("Catalogo") // Action: Leer catalogo de clientes
      val botonDemanda = new Button("Demanda") // Action: Leer archivo de Demanda
      val botonExport = new Button("Exportar por ubicación") // Exportar resultados por ubicacion
      val botonExportTotal = new Button("Exportar total") // Exportar resultados totales
      
      val LabelCatalogo = new Label // Action: Muestra path de archivo seleccionado

      botonCatalogo.onAction = (ae: ActionEvent) => { 
        //Escoge arcivo, path es accesible fuera del eventHandler 
        val fileChooser = new FileChooser
        val selectedFile = fileChooser.showOpenDialog(stage)
        LabelCatalogo.text = selectedFile.toString()
        botonCatalogo.accessibleText_=(selectedFile.toString())
        val catalogo =  leer(selectedFile.toString())
        catalogo.show()
      }
  
      val Catalogo = new HBox // Contiene botonCatalogo
      Catalogo.setAlignment(Pos.Center)
      Catalogo.setPadding(Insets(25))
      Catalogo.setSpacing(20)
      Catalogo.getChildren().addAll(botonCatalogo, LabelCatalogo)
     
      val LabelDemanda = new Label //Action: Muestra path de archivo seleccionado

      botonDemanda.onAction = (ae: ActionEvent) => {
        //Escoge archivo demanda, path es accesible fuera del eventHandler
        val fileChooser1 = new FileChooser
        val selectedFile1 = fileChooser1.showOpenDialog(stage)
        LabelDemanda.text = selectedFile1.toString()
        botonDemanda.accessibleText_=(selectedFile1.toString())

      val demanda = leer(selectedFile1.toString()) 

          demanda.show()

      val location = new ComboBox(unique(demanda).toList) // dropdown list - selecciona ubicacion
       Exportar.children += location
          location.onAction = (ae: ActionEvent) => { //Al seleccionar ubicacion, generar boton Exportar resultados por ubic
            Exportar.children += botonExport
            println(location.value.apply)
          }
         
         Export.children += botonExportTotal // Boton exportar resultados total
         vbox.children ++= List(Exportar, Export)
         
      }


      val Demanda = new HBox // Contiene botonDemanda
      Demanda.setAlignment(Pos.Center)
      Demanda.setPadding(Insets(25))
      Demanda.setSpacing(20)
      Demanda.getChildren().addAll(botonDemanda, LabelDemanda)

      //Exportar resultados
      val LabelExport = new Label

      botonExport.onAction = (ae: ActionEvent) => { //Selecciona path para resultados por ubicacion
        val fileChooser2 = new FileChooser
        val selectedFile2 = fileChooser2.showSaveDialog(stage)
        LabelExport.text = selectedFile2.toString()
        botonExport.accessibleText_=(selectedFile2.toString())
      }

      
      val Export = new HBox // Contiene botonExport
      Export.setAlignment(Pos.Center)
      Export.setPadding(Insets(25))
      Export.setSpacing(20)
    
      botonExportTotal.onAction = (ae: ActionEvent) => { // Selecciona path para resultados totales
           val fileChooser3 = new FileChooser
           val selectedFile3 = fileChooser3.showSaveDialog(stage)
      }

      val Exportar = new HBox //Contiene botonExportarTotal y location
      Exportar.setAlignment(Pos.Center)
      Exportar.setPadding(Insets(25))
      Exportar.setSpacing(10)

      val vbox = new VBox{
          spacing = 5
          alignment = Pos.Center
          children = List(Catalogo, Demanda)
      }//Contiene Demanda, Catalogo, Export y Exportar
      
      val rootPane = new BorderPane // contenido de Scene
      rootPane.center = vbox
      rootPane.top = menuBar
      root = rootPane
// ¿Cuándo llamar las funciones de Parametros? ¿Como acceder a ellas si son llamadas en un eventHandler?
    }
  }
}
