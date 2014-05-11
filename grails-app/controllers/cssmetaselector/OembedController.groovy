package cssmetaselector


class OembedController {

    def index = {
        if (params.url) {
            String templateID = URLDecoder.decode(params.url.toString()).split("/").last()
            def template = Template.get(Long.parseLong(templateID))
            if (template != null) {
                def oembedJSON = "{" +
                                    "\"type\": \"rich\", " +
                                    "\"author_name\": \"${ template.user.username }\", " +
                                    "\"height\": 270, " +
                                    "\"html\": \"\\u003ciframe width=\\\"480\\\" height=\\\"270\\\" src=\\\"http:\\/\\/metastyle.cfapps.io\\/static\\/showSnippet\\/${ template.id }\\\" frameborder=\\\"0\\\" allowfullscreen\\u003e\\u003c\\/iframe\\u003e\", " +
                                    "\"title\": \"CSS template for ${ template.typeURI } in ${ template.format }\", " +
                                    "\"author_url\": \"http:\\/\\/metastyle.cfapps.io\\/static\\/showGuestTemplate\\/${ template.user.id }\", " +
                                    "\"provider_name\": \"Metastyle\", " +
                                    "\"width\": 480, " +
                                    "\"provider_url\": \"http:\\/\\/metastyle.cfapps.io\\/\"" +
                                "}"

                def oembedXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<oembed>" +
                                "   <author_name>${ template.user.username }</author_name>" +
                                "   <html>&lt;iframe width=\"480\" height=\"270\" src=\"http://metastyle.cfapps.io/static/showSnippet/${ template.id }\" frameborder=\"0\" allowfullscreen&gt;&lt;/iframe&gt;</html>" +
                                "   <author_url>http://metastyle.cfapps.io/static/showGuestTemplate/${ template.user.id }</author_url>" +
                                "   <provider_url>http://metastyle.cfapps.io/</provider_url>" +
                                "   <type>rich</type>" +
                                "   <height>270</height>" +
                                "   <title>CSS template for ${ template.typeURI } in ${ template.format }</title>" +
                                "   <provider_name>Metastyle</provider_name>" +
                                "   <width>480</width>" +
                                "</oembed>"
                if (params?.format) {
                    if (params.format.toString().equals("json")) {
                        response.setContentType("application/json")
                        render(oembedJSON)
                    }
                    else if (params.format.toString().equals("xml")) {
                        response.setContentType("text/xml")
                        render(oembedXML)
                    }
                    else {
                        render(status: 404, text: "Format not supported.")
                    }
                }
                else {
                    System.out.println("format: " + request.format)
                    if (request.format) {
                        if (request.format == "xml") {
                            response.setContentType("text/xml")
                            render(oembedXML)
                        }
                        else {
                            response.setContentType("application/json")
                            render(oembedJSON)
                        }
                    }
                    else {
                        response.setContentType("application/json")
                        render(oembedJSON)
                    }
                }
            }
            else {
                render(status: 404, test: "Microtheme not found.")
            }
        }
        else {
            render(status: 404, test: "Please specify the microtheme URL (e.g., ?url=http://www.example.com/).")
        }
    }

    def tester = {
        render(view: "tester")
    }
}
