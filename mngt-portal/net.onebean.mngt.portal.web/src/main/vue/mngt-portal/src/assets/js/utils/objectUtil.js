var objectUtil = {}

// 克隆对象
objectUtil.copyObj = function(mainObj) {
  let objCopy = {}
  let key
  for (key in mainObj) {
    // 将每个属性复制到objCopy对象
    objCopy[key] = mainObj[key]
  }
  return objCopy
}

// 数组去重
objectUtil.uniqueArray = function(array) {
  var result = [array[0]]
  for (var i = 1; i < array.length; i++) {
    var item = array[i]
    var repeat = false
    for (var j = 0; j < result.length; j++) {
      if (item === result[j]) {
        repeat = true
        break
      }
    }
    if (!repeat) {
      result.push(item)
    }
  }
  return result
}

//去除数组空字符串
objectUtil.removeEmptyValueInArray = function(parkingList) {
  for (var i = 0; i < parkingList.length; i++) {
    if (
      parkingList[i] == '' ||
      parkingList[i] == null ||
      typeof parkingList[i] == undefined
    ) {
      parkingList.splice(i, 1)
      i = i - 1
    }
  }
  return this.uniqueArray(parkingList)
}

Array.prototype.indexOf = function(val) {
  for (var i = 0; i < this.length; i++) {
    if (this[i] == val) return i
  }
  return -1
}

Array.prototype.remove = function(val) {
  var index = this.indexOf(val)
  if (index > -1) {
    this.splice(index, 1)
  }
}

export default objectUtil
