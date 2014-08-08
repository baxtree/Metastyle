package cssmetaselector

import org.apache.commons.logging.LogFactory

class CustomElementController {

    private static final LOGGER = LogFactory.getLog(this)

    def index = {
        LOGGER.info("Custom element controller got called")
        if (params.url) {
            String templateID = URLDecoder.decode(params.url.toString()).split("/").last()
            def template = Template.get(Long.parseLong(templateID))
            def shadowDOMStr = "\r\n<metastyle-widget>This is a Metastyle Widget</metastyle-widget>\r\n"
            shadowDOMStr +=  "<template id=\"widget-template-${ templateID }\">\r\n" +
                    "<style type=\"text/css\">\r\n" +
                    template.cssTemplate + "\r\n" +
                    "</style>\r\n"
            shadowDOMStr += (template.testSnippet =~ /<body[^>]*>((.|[\n\r])*)<\/body>/)[0][1] + "\r\n" + "</template>\r\n"
            shadowDOMStr += "<script type=\"text/javascript\">\r\n" +
                    "    var template = document.getElementById(\"widget-template-${ templateID }\");\r\n" +
                    "    var MetastyleWidgetPrototype = Object.create(HTMLElement.prototype);\r\n" +
                    "    MetastyleWidgetPrototype.createdCallback = function () {\r\n" +
                    "        var shadow = this.createShadowRoot();\r\n" +
                    "        shadow.appendChild(template.content.cloneNode(true));\r\n" +
                    "    };\r\n" +
                    "    var metastyleWidget = document.registerElement(\"metastyle-widget\", {\r\n" +
                    "        prototype: MetastyleWidgetPrototype\r\n" +
                    "    });\r\n" +
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
