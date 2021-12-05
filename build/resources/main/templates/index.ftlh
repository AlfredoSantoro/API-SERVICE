<#-- @ftlvariable name="version" type="java.lang.String" -->
<#-- @ftlvariable name="ods_version" type="java.lang.String" -->
<#-- @ftlvariable name="onBDSchema" type="java.lang.String" -->
<#-- @ftlvariable name="connectionStatus" type="java.lang.Boolean" -->
<!DOCTYPE HTML>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="noindex,nofollow">
    <meta name="googlebot" content="noindex">
    <title>UniSesaLab Web Service</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
</head>

<body>

<div class="container" style="margin-top: 20px">
    <div class="container" style="margin-top: 80px">
        <div class="jumbotron">
            <h2 style="font-size: 50px">UNISA SESA LAB</h2>
            <p>Sesa Lab API</p>
            <div>
                <small style="color: darkgray">API v.<span id="version">${version}</span></small>
                - DB2 On Cloud
                <#if connectionStatus == true>
                    <span> - Connected on <span style="color: dodgerblue">${onBDSchema}</span> </span>
                <#else >
                    <span style="color: red"> is not connected </span>
                </#if>
                </small>
            </div>
            <br/>
            <p><a class="btn btn-primary btn-lg" href="/#" role="button">API Documentation</a></p>
        </div>
        <div class="pull-right">
            <small><a href="/#">Terms and Conditions</a></small>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>

</body>

</html>
