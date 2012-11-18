<div class="authentication offset6">
	<g:if test="${ session.user != null }">
		<span class="span4"><i class="icon-user"></i>Welcome, ${ session.user.username}! | <g:link controller="user" action="userLogout"><i class="icon-signout"></i>Log Out</g:link></span>
	</g:if>
	<g:else>
		<span class="span4"><g:link controller="static" action="login"><i class="icon-signin"></i>Log In</g:link> | <g:link controller="static" action="register"><i class="icon-edit"></i>Register</g:link></span>
	</g:else>
</div>