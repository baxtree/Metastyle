<g:render template="../templates/header" />
<g:render template="../templates/banner" />
	<section id="menu-bar">
		<div class="container">
			<div class="row">
				<div class="span6">
					<ul class="navbar navbar-inverse navbar-fixed-top">
						<div class="navbar-inner">
					        <div class="container">
					          <g:link class="brand" controller="static" action="welcome">Metastyle</g:link>
					          <div class="nav-collapse collapse">
					            <ul class="nav">
					              <li class="">
					                <g:link controller="static" action="welcome"><i class="icon-home"></i>Home</g:link>
					              </li>
					              <li class="">
					                <g:link controller="static" action="getSkeletons"><i class="icon-wrench"></i>Get Skeletons</g:link>
					              </li>
					              <li class="">
					                <g:link controller="user" action="showTemplates"><i class="icon-folder-close"></i>My Micro-Themes</g:link>
					              </li>
					            </ul>
					            <g:render template="../templates/authentication" />
					          </div>
					        </div>
					    </div>
					</ul>
				</div>
			</div>
		</div>
	</section>
	<section id="main-container" class="contact span8 offset2">
	<p class="intro">This tool is in active developement at the <a href="http://www.ed.ac.uk/schools-departments/informatics/">School of Informatics, University of Edinburgh</a>.
	</p>
	<p/>
	<p class="intro">
	    Room 2.05, Informatics Forum<br>
	    10 Crichton Street<br>
	    Edinburgh EH8 8PD, UK<br>
	    <em><a href="mailto:metastyle.helpline@gmail.com">Email</a></em><br/><br/>
	    <iframe width="425" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q=School+of+Informatics,+The+University+of+Edinburgh,+Edinburgh,+United+Kingdom&amp;aq=&amp;sll=55.944322,-3.185992&amp;sspn=0.001472,0.004823&amp;t=h&amp;ie=UTF8&amp;hq=School+of+Informatics,+The+University+of+Edinburgh,+Edinburgh,+United+Kingdom&amp;ll=55.944632,-3.187505&amp;spn=0.012497,0.032015&amp;output=embed"></iframe><br /><small><a href="https://maps.google.com/maps?f=q&amp;source=embed&amp;hl=en&amp;geocode=&amp;q=School+of+Informatics,+The+University+of+Edinburgh,+Edinburgh,+United+Kingdom&amp;aq=&amp;sll=55.944322,-3.185992&amp;sspn=0.001472,0.004823&amp;t=h&amp;ie=UTF8&amp;hq=School+of+Informatics,+The+University+of+Edinburgh,+Edinburgh,+United+Kingdom&amp;ll=55.944632,-3.187505&amp;spn=0.012497,0.032015" style="color:#0000FF;text-align:left">View Larger Map</a></small>
	</p>
	</section>
<g:render template="../templates/footer" />