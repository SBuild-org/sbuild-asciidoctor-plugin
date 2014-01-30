import de.tototec.sbuild._

@version("0.7.1")
// @classpath("mvn:org.sbuild:org.sbuild.plugins.asciidoctor:0.1.0")
@classpath("../org.sbuild.plugins.asciidoctor/target/org.sbuild.plugins.asciidoctor-0.1.0.jar")
class SBuild(implicit _project: Project) {

  Target("phony:clean") exec {
    Path("target").deleteRecursive
  }

  Plugin[org.sbuild.plugins.asciidoctor.Asciidoctor] configure {_.copy(
    verbose = true,
    sectionNumbers = true,
    attributes = Map("toc" -> "")
  )}

}
