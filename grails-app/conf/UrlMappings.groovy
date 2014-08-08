class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/admin/cfDashboard/$action?"(controller: 'cloudFoundryDashboard')

		"/admin/cfDashboard/application/$appName"(
              controller: 'cloudFoundryDashboard', action: 'application')

		"/admin/cfDashboard/service/$serviceName"(
              controller: 'cloudFoundryDashboard', action: 'service')

		"/admin/cfDashboard/files/$appName/$instanceIndex?"(
              controller: 'cloudFoundryDashboard', action: 'files')
        "/shadow-dom"(controller: "shadowDOM")
        "/shadow-dom/example"(controller: "shadowDOM", action: "tester")
        "/custom-element"(controller: "customElement")
        "/custom-element/example"(controller: "customElement", action: "tester")
//        "/shadowdom/tester"(controller: "shadowDOM", action: "tester")
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
