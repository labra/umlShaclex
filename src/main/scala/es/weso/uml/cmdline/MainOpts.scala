package es.weso.uml.cmdline

import org.rogach.scallop._
import es.weso.schema._
import es.weso.rdf.jena.RDFAsJenaModel

class MainOpts(
                arguments: Array[String],
                onError: (Throwable, Scallop) => Nothing
              ) extends ScallopConf(arguments) {

  lazy val schemaFormats = Schemas.availableFormats.map(_.toUpperCase).distinct
  lazy val dataFormats = RDFAsJenaModel.availableFormats.map(_.toUpperCase).distinct
  lazy val engines = Schemas.availableSchemaNames.map(_.toUpperCase) ++ List("None")// List("SHEX","SHACL")
  lazy val defaultSchemaFormat = "TURTLE"
  lazy val defaultDataFormat = "TURTLE"
  lazy val defaultEngine = engines.head

  banner("""| umlShaclex: SHACL/ShEx processor
            | Options:
            |""".stripMargin)

  footer("Enjoy!")

  val schema = opt[String](
    "schema",
    short = 's',
    default = None,
    descr = "schema file")

  val schemaFormat = opt[String](
    "schemaFormat",
    noshort = true,
    default = Some(defaultSchemaFormat),
    descr = s"Schema format. Default ($defaultDataFormat) Possible values: ${schemaFormats.mkString(",")}",
    validate = isMemberOf(schemaFormats))

  val schemaUrl = opt[String](
    "schemaUrl",
    noshort = true,
    default = None,
    descr = "schema Url")


  val outputFile = opt[String](
    "outFile",
    default = None,
    descr = "save report to file",
    short = 'f')

  val engine = opt[String](
    "engine",
    default = Some(defaultEngine),
    descr = s"Engine. Default ($defaultEngine). Possible values: ${engines.mkString(",")}",
    validate = isMemberOf(engines))

  val baseFolder = opt[String](
    "baseFolder",
    default = None,
    descr = "base folder",
    short = 'b')


  override protected def onError(e: Throwable) = onError(e, builder)

  def isMemberOf(ls: List[String])(x: String): Boolean =
    ls contains (x.toUpperCase)

}