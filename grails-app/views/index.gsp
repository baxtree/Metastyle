<g:if test="${ session.user != null }">
	${ response.sendRedirect('user/showHome') }
</g:if>
<g:else>
	${ response.sendRedirect('static/welcome') }
</g:else>