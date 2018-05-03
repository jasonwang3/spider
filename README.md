# spider
Web crawler based on akka and scala

<img src="https://codeship.com/projects/316114a0-2e48-0134-5a24-12bd9e093a4a/status?branch=master"/>


## API Example ##

    {
      request: {
        url: "http://xxx.com"
      },
      site: {
        domain: {String},
        userAgent: {String},
        cookies: {
          {domain}: {
            "param1": {String} // JSESSIONID:sessionId
            ...
          }
        }
      },
      rules: [
        //get HTML or get URL goto next page, this is ordered
        {
          action: "GET_URL/GET_CONTENT/GET_HTML",
          matchRule: [
            {
              selectorType: {String} // "CSS/XPATH/REGEX/LINK",
              rule: {String}
            }
          ]
        },
        ...
        {	
          action: "GET_URL/GET_CONTENT/GET_HTML",
          matchRule: [
            {
              selectorType: {String} // "CSS/XPATH/REGEX/LINK",
              rule: {String}
            }
          ],
          //last rule must add field "contentSelecotrs"
          contentSelectors: [
            {
              paramName: "Value2",
              matchRule: [
                {
                  selectorType: {String} // "CSS/XPATH/REGEX/LINK",
                  rule: {String}
                }
              ]
            },
            {
              paramName: "value2",
              matchRule: [
                {
                  selectorType: {String} // "CSS/XPATH/REGEX/LINK",
                  rule: {String}
                }
              ]
            }            
          ]
        }   
      ]
    }

