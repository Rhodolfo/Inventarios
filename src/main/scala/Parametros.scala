package org.arena.JumexHerramienta

object Parametros extends SparkTrait{
     import org.apache.spark.sql.{DataFrame, Row}
     import org.apache.spark.sql.functions._
     import spark.implicits._

    // Calcula parámetros de demanda dado un archivo de demanda por semana en formato csv
     
    
    def leer(Path: String): DataFrame = { //Lee dataset en formato csv
          spark.read.format("com.databricks.spark.csv")
                    .option("inferSchema", "true")
                    .option("header", "true")
                    .option("sep", "|")
                    .load("file:///".concat(Path))
     }

   def unique(data: DataFrame): Array[Row]  = { // ubicaciones únicas para Combobox

      data.select("Ubicacion").distinct().collect()
   
   }

   // Calcula demanda total, demanda media por semana, desviacion estandar de demanda

   def paramDemanda(Path: String, Ubicacion: String, data: DataFrame): DataFrame = {
          var DF = leer(Path)
          DF.filter(DF("Ubicacion") === Ubicacion)
          DF.groupBy("SKU", "Ubicacion").agg(sum("Demanda"), avg("Demanda"), 
                                            stddev("Demanda"))
   }

}
