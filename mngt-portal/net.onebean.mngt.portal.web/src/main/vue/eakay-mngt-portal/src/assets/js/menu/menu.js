import utils from '../utils/netUtil'
import apiPath from '../constant/ApiPath'

var menu = {}

menu.getMenuList = async function(){
    const menu =  await utils.postAsync(apiPath.getMenuList, {})
    return menu
}

export default menu