<#list marathons as marathon>
    upstream ${marathon.nodeName} {
    <#list marathon.nodes as instance>
        server ${instance.physicalPath};
    </#list>
    }

</#list>