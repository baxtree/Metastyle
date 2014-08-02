package cssmetaselector

import org.apache.commons.logging.LogFactory

class OembedController {

    private static final LOGGER = LogFactory.getLog(this)

    def index = {
        LOGGER.info("oEmbed controller got called")
        if (params.url) {
            String templateID = URLDecoder.decode(params.url.toString()).split("/").last()
            def template = Template.get(Long.parseLong(templateID))
            if (template != null) {
                def oembedJSON = "{" +
                                    "\"type\": \"rich\", " +
                                    "\"author_name\": \"${ template.user.username }\", " +
                                    "\"height\": 220, " +
                                    "\"html\": \"\\u003ciframe width=\\\"280\\\" height=\\\"220\\\" src=\\\"" + createLink(controller: "static", action: "showSnippet", id: "${ template.id }", absolute: "true").toString().replace("/", "\\/") + "\\\" frameborder=\\\"0\\\" allowfullscreen\\u003e\\u003c\\/iframe\\u003e\", " +
                                    "\"title\": \"CSS template for ${ template.typeURI } in ${ template.format }\", " +
                                    "\"author_url\": \"http:\\/\\/metastyle.cfapps.io\\/static\\/showGuestTemplate\\/${ template.user.id }\", " +
                                    "\"provider_name\": \"Metastyle\", " +
                                    "\"width\": 280, " +
                                    "\"provider_url\": \"http:\\/\\/metastyle.cfapps.io\\/\"" +
                                "}"

                def oembedXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<oembed>" +
                                "   <author_name>${ template.user.username }</author_name>" +
                                "   <html>&lt;iframe width=\"280\" height=\"220\" src=\"${ createLink(controller: "static", action: "showSnippet", id: "${ template.id }", absolute: "true").toString() }\" frameborder=\"0\" allowfullscreen&gt;&lt;/iframe&gt;</html>" +
                                "   <author_url>http://metastyle.cfapps.io/static/showGuestTemplate/${ template.user.id }</author_url>" +
                                "   <provider_url>http://metastyle.cfapps.io/</provider_url>" +
                                "   <type>rich</type>" +
                                "   <height>220</height>" +
                                "   <title>CSS template for ${ template.typeURI } in ${ template.format }</title>" +
                                "   <provider_name>Metastyle</provider_name>" +
                                "   <width>280</width>" +
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
                    LOGGER.info("format: " + request.format)
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
                render(status: 404, text: "Microtheme not found.")
            }
        }
        else {
            render(status: 404, text: "Please specify the micro-theme URL (e.g., ?url=http://www.example.com/).")
        }
    }

    def tester = {
        render(view: "tester")
    }
}
