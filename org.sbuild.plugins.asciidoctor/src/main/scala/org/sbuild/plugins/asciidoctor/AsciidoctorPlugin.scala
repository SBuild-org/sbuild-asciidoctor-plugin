package org.sbuild.plugins.asciidoctor

import de.tototec.sbuild._
import de.tototec.sbuild.addons.support.ForkSupport

class AsciidoctorPlugin(implicit project: Project) extends Plugin[Asciidoctor] {

  override def create(name: String): Asciidoctor = {
    val sourceDir = Path("src") / (if (name == "") "asciidoc" else s"asciidoc-${name}")
    val targetDir = Path("target") / (if (name == "") "asciidoc" else s"asciidoc-${name}")
    Asciidoctor(
      generateTargetName = if (name == "") "asciidoc" else s"asciidoc-${name}",
      sourceFiles = s"scan:${sourceDir}",
      targetDir = Some(targetDir)
    )
  }

  override def applyToProject(instances: Seq[(String, Asciidoctor)]): Unit = instances foreach {
    case (name, adoc) =>

      val adocCp: TargetRefs =
        "mvn:org.asciidoctor:asciidoctor-java-integration:0.1.4" ~
          "mvn:com.beust:jcommander:1.32" ~
          "mvn:org.jruby:jruby-complete:1.7.9"

      var cmdline = Seq[String]()
      if (!adoc.attributes.isEmpty)
        adoc.attributes.map {
          case (k, v) =>
            cmdline ++= Seq("-a", if (v.isEmpty()) k else s"$k=$v")
        }
      if (adoc.compactOutput) cmdline ++= Seq("--compact")
      if (adoc.verbose) cmdline ++= Seq("--verbose")
      adoc.backend.map { b => cmdline ++= Seq("--backend", b) }
      adoc.baseDir.map { dir => cmdline ++= Seq("--base-dir", dir.getPath()) }
      adoc.targetDir.map { dir => cmdline ++= Seq("--destination-dir", dir.getPath()) }
      adoc.docType.map { t => cmdline ++= Seq("--doctype", t) }
      adoc.safeMode.map { m => cmdline ++= Seq("--safe-mode", m) }
      if (adoc.sectionNumbers) cmdline ++= Seq("--section-numbers")
      adoc.templateDir.map { dir => cmdline ++= Seq("template-dir", dir.getPath()) }

      Target("phony:adoc-help") dependsOn adocCp exec {
        ForkSupport.runJavaAndWait(
          classpath = adocCp.files,
          arguments = Array("org.asciidoctor.cli.AsciidoctorInvoker", "--help"),
          failOnError = true
        )
      }

      Target("phony:" + adoc.generateTargetName) dependsOn adocCp ~ adoc.sourceFiles exec {
        adoc.targetDir.map(_.mkdirs)

        val baseDir = Path(".")

        ForkSupport.runJavaAndWait(
          classpath = adocCp.files,
          arguments = (
            "org.asciidoctor.cli.AsciidoctorInvoker" +:
            cmdline ++:
            adoc.sourceFiles.files.flatMap(_.pathRelativeTo(baseDir))
          ).toArray,
          failOnError = true
        )
      }

  }
}