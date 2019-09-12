jQuery.extend({
/**
    * Returns get parameters.
    *
    * If the desired param does not exist, null will be returned
    *
    * @example value = $.getURLParam("paramName");
    */
    getURLParam: function(strParamName)
    {
        var strReturn = "";
        var strHref = window.location.href.toUpperCase();

        var bFound = false;

        var cmpstring = strParamName.toUpperCase() + "=";
        var cmplen = cmpstring.length;

        if (strHref.indexOf("?") > -1)
        {
            var strQueryString = strHref.substr(strHref.indexOf("?") + 1);0
            var aQueryString = strQueryString.split("&");
            for (var iParam = 0; iParam < aQueryString.length; iParam++)
            {
                if (aQueryString[iParam].substr(0, cmplen) == cmpstring)
                {
                    var aParam = aQueryString[iParam].split("=");
                    strReturn = aParam[1];
                    bFound = true;
                    break;
                }

            }
        }
        if (bFound == false) return null;
        //  www.permadi.com/tutorial/urlEncoding/
        //<space> %20  or + 
       // return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g, '%22').replace(/\'/g, '%27').replace(/\//g, '%2F');
        strReturn = strReturn.replace(/\+/g, '%20');
        return unescape(strReturn);
    },
    getStringParam: function(strParamName, strParamString)
    {
        var strReturn = "";
        var strHref = strParamString.toUpperCase();
        var bFound = false;

        var cmpstring = strParamName.toUpperCase() + "=";
        var cmplen = cmpstring.length;

        var strQueryString = strHref;
        var aQueryString = strQueryString.split("&");
        for (var iParam = 0; iParam < aQueryString.length; iParam++)
        {
            if (aQueryString[iParam].substr(0, cmplen) == cmpstring)
            {
                var aParam = aQueryString[iParam].split("=");
                strReturn = aParam[1];
                bFound = true;
                break;
            }

        }

        if (bFound == false) return null;
        return strReturn;
    }
});