package cssmetaselector

import org.apache.commons.logging.LogFactory

class ShadowDOMController {

    private static final LOGGER = LogFactory.getLog(this)

    def index = {
        LOGGER.info("Shadow dom controller got called")
        if (params.url) {
            String templateID = URLDecoder.decode(params.url.toString()).split("/").last()
            def template = Template.get(Long.parseLong(templateID))
            def shadowDOMStr = "\r\n<div id=\"metastyle-widget-${ templateID }\">This is a Metastyle widget</div>\r\n"
            shadowDOMStr +=  "<template id=\"widget-template-${ templateID }\">\r\n" +
                        "<style type=\"text/css\">\r\n" +
                        template.cssTemplate + "\r\n" +
                        "</style>\r\n"
            shadowDOMStr += (template.testSnippet =~ /<body[^>]*>((.|[\n\r])*)<\/body>/)[0][1] + "\r\n" + "</template>\r\n"
            shadowDOMStr += "<script type=\"text/javascript\">\r\n" +
                    "\twindow.onload = function () {\r\n" +
                    "\t\tvar shadow = document.getElementById(\"metastyle-widget-${ templateID }\").createShadowRoot();\r\n" +
                    "\t\tvar template = document.getElementById(\"widget-template-${ templateID }\");\r\n" +
                    "\t\tshadow.appendChild(template.content.cloneNode(true));\r\n" +
                    "\t}\r\n" +
                    "</script>\r\n"
            render(status: 200, contentType: "text/html", text: shadowDOMStr)

        }
        else {
            render(status: 404, text: "Please specify the microtheme URL (e.g., ?url=http://www.example.com/).")
        }
    }

    def tester = {
        render(view: "example")
    }
}
