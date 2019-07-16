let styleUtil = {}


styleUtil.initTableListRowClass = function(index) {
    if (index == 0) {
      return ''
    }
    if ((index == 1 || index % 5 == 0 || index % 9 == 0) && index % 2 != 0) {
      return 'table-info-row'
    }
    if ((index % 3 == 0 || index % 7 == 0) && index % 2 != 0) {
      return 'table-error-row'
    }
    return ''
  }

export default styleUtil