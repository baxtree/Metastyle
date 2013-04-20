<html>
<head><title>Your Profile</title></head>
<body>
<a class="home" href="${createLinkTo(dir:'')}">Home</a>
<g:hasOauthError>
    <div class="errors">
        <g:renderOauthError/>
    </div>
</g:hasOauthError>
 
<g:form url="[action:'change',controller:'profile']" method="get">
    Your LinkedIn Profile:
    <textarea id="payload" style="width: 100%; height: 50%; color: red">${profileXML}</textarea>
    <p>
        <g:textField name="apiUrl" value="${apiUrl}" size="100%"/>
        <br/>
        <g:submitButton name="send" value="Send Request"/>
    </p>
</g:form>
</body>
</html>
