<g:render template="../templates/header" />
<g:render template="../templates/banner" />
<section id="oembed-tester">
    <div class="container">
        %{--<a class="oembed" href="${createLink(controller: 'template', action: 'showTemplate', id: '1')}"></a>--}%
        <a class="oembed" href="http://localhost:8080/metastyle/template/showTemplate/1"></a>
        <div id="progress">Loading microtheme...</div>
    </div>
</section>
<g:render template="../templates/footer" />