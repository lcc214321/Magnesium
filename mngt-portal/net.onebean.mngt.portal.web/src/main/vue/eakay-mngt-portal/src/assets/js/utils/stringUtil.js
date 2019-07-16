let stringUtil = {}

stringUtil.getDomainByUrl = url => {
  let domain = url.split('/')
  if (domain[2]) {
    domain = domain[2]
  } else {
    domain = ''
  }
  return domain
}


stringUtil.getHostByUrl = url => {
    let domain = stringUtil.getDomainByUrl(url)
    if (domain.indexOf(':') != -1) {
        domain = domain.substr(0,domain.indexOf(':'))
    }
    return domain
  }

export default stringUtil
