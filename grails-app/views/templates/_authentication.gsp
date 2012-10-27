<div class="authentication offset8">
	<g:if test="${ session.user != null }">
		<span>Welcome, ${ session.user.username} !</span>
	</g:if>
	<g:else>
		<span><g:link controller="static" action="login">Log in</g:link> | <g:link controller="static" action="register">Register</g:link></span>
	</g:else>
</div>