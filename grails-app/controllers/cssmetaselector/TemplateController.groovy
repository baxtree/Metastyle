package cssmetaselector

class TemplateController {

	def scaffold = Template
	
	def showTemplate = {
		def template = Template.get(params.id)
		render(view: "template-view", model: [
			template: template.cssTemplate,
			tem_targetedType: template.typeURI,
			tem_schema: template.contextURL,
			tem_format: template.format,
			tem_prefix: template.prefix,
			tem_contributor: template.user.username])	
	}
}
