<g:render template="../templates/header" />
<g:render template="../templates/banner" />

<div id="metastyle-widget-1">This is a Metastyle widget</div>
<template id="widget-template-1">
    <style type="text/css">
        /*style for the Person (http://schema.org/Person) as Microdata*/
        /*style for the element of type Person*/
        /*style for the name of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="name"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="name"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="name"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="name"] {
        font-size: 1.2em;
        font-weight: bold;
        text-transform: none;
        color: #000033;
    }
        /*style for the address of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="address"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="address"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="address"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="address"] {
        margin: 10px 10px 0px 0px;
        color: #000033;
    }
        /*style for the telephone of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="telephone"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="telephone"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="telephone"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="telephone"] {
        margin: 10px 10px 0px 0px;
        color: #000033;
    }
        /*style for the email of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="email"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="email"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="email"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="email"] {
        color: #000033;
        text-decoration: underline;
    }
        /*style for the email of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="email"]:hover,
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="email"]:hover,
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="email"]:hover,
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="email"]:hover {
        color: red;
        text-decoration: underline;
    }
        /*style for the deathDate of the Person*/
        /*style for the alumniOf of the Person*/
        /*style for the url of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="url"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="url"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="url"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="url"] {
        color: #000033;
        text-decoration: underline;
    }
        /*style for the url of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="url"]:hover,
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="url"]:hover,
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="url"]:hover,
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="url"]:hover {
        color: red;
        text-decoration: underline;
    }
        /*style for the additionalName of the Person*/
        /*style for the homeLocation of the Person*/
        /*style for the description of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="description"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="description"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="description"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="description"] {
        margin-top: 10px;
        padding-top: 10px;
        border-top: 1px dotted #cccccc;
    }
        /*style for the nationality of the Person*/
        /*style for the sibling of the Person*/
        /*style for the follows of the Person*/
        /*style for the siblings of the Person*/
        /*style for the colleagues of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="colleagues"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="colleagues"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="colleagues"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="colleagues"] {
        color: #000033;
        text-decoration: underline;
    }
        /*style for the colleagues of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="colleagues"]:hover,
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="colleagues"]:hover,
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="colleagues"]:hover,
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="colleagues"]:hover {
        color: red;
        text-decoration: underline;
    }
        /*style for the memberOf of the Person*/
        /*style for the knows of the Person*/
        /*style for the name of the Person*/
        /*style for the gender of the Person*/
        /*style for the birthDate of the Person*/
        /*style for the children of the Person*/
        /*style for the familyName of the Person*/
        /*style for the jobTitle of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="jobTitle"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="jobTitle"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="jobTitle"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="jobTitle"] {
        margin: 10px 10px 0px 0px;
        color: #666;
    }
        /*style for the workLocation of the Person*/
        /*style for the parents of the Person*/
        /*style for the affiliation of the Person*/
        /*style for the givenName of the Person*/
        /*style for the honorificPrefix of the Person*/
        /*style for the parent of the Person*/
        /*style for the colleague of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="colleague"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="colleague"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="colleague"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="colleague"] {
        color: #000033;
        text-decoration: underline;
    }
        /*style for the colleague of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="colleague"]:hover,
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="colleague"]:hover,
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="colleague"]:hover,
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="colleague"]:hover {
        color: red;
        text-decoration: underline;
    }
        /*style for the additionalType of the Person*/
        /*style for the honorificSuffix of the Person*/
        /*style for the image of the Person*/
    [itemscope][itemtype="http://schema.org/Person"][itemprop="image"],
    [itemscope][itemtype="http://schema.org/Person"] [itemprop="image"],
    [itemscope] [itemtype="http://schema.org/Person"][itemprop="image"],
    [itemscope] [itemtype="http://schema.org/Person"] [itemprop="image"] {
        float: left;
        margin-left: 0px;
        border: 1px solid #333333;
        width: 64px;
        height: 64px;
    }
        /*style for the worksFor of the Person*/
        /*style for the relatedTo of the Person*/
        /*style for the spouse of the Person*/
        /*style for the performerIn of the Person*/

    </style>

    <div itemscope itemtype="http://schema.org/Person">
        <span itemprop="name">Jane Doe</span>
        <img src="http://wikieducator.org/images/1/16/Dummy_user.png" itemprop="image" />

        <span itemprop="jobTitle">Professor</span>
        <div itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
            <span itemprop="streetAddress">
                20341 Whitworth Institute
                405 N. Whitworth
            </span>
            <span itemprop="addressLocality">Seattle</span>,
            <span itemprop="addressRegion">WA</span>
            <span itemprop="postalCode">98052</span>
        </div>
        <span itemprop="telephone">(425) 123-4567</span>
        <a href="mailto:jane-doe@xyz.edu" itemprop="email">
            jane-doe@xyz.edu</a>

        Jane's home page:
        <a href="http://www.janedoe.com" itemprop="url">janedoe.com</a>

        Graduate students:
        <a href="http://www.xyz.edu/students/alicejones.html" itemprop="colleague">
            Alice Jones</a>
        <a href="http://www.xyz.edu/students/bobsmith.html" itemprop="colleague">
            Bob Smith</a>
    </div>

</template>
<script type="text/javascript">
    window.onload = function () {
        var shadow = document.getElementById("metastyle-widget-1").createShadowRoot();
        var template = document.getElementById("widget-template-1");
        shadow.appendChild(template.content.cloneNode(true));
    }
</script>

<g:render template="../templates/footer" />