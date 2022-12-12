#!groovy
import groovy.json.JsonSlurperClassic

/**
 * Retrieve environments names from environments.json as a string with \n separator.
 * Element 'none' is added as first in the list to avoid mis-deployment
 * @return environment names separated by \n
 */
def call(String scope = 'rg', onlyOps = false) {
  String json = libraryResource "${scope}-environments.json"
  def environments = new JsonSlurperClassic().parseText(json)
  def devEnvs = environments.findAll { !(onlyOps && it.value['opsUsersOnly']) }
  def environmentNames = devEnvs.keySet().sort()
  return ['none'].plus(environmentNames).join('\n')
}
