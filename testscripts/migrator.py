import logging
import boto3
import requests
from requests.adapters import HTTPAdapter
from requests.packages.urllib3.util.retry import Retry

import config

retries = Retry(total=10, connect=10, backoff_factor=1)
s = requests.Session()
s.mount('http://', HTTPAdapter(max_retries=retries))
s.mount('https://', HTTPAdapter(max_retries=retries))


def main():
    dynamodb = boto3.resource('dynamodb', endpoint_url=config.DYNAMODB_ENDPOINT, region_name=config.AWS_REGION)
    table = dynamodb.Table(config.DYNAMODB_TABLE)
    url = config.BROADCAST_HOST + config.BROADCAST_PATH

    for item in table.scan()['Items']:
        payload = {'institution': item['id']}
        if 'idpid' in item:
            payload['idpid'] = item['idpid']
        logging.info("trying {}".format(url))
        s.post(url, json=payload)


if __name__ == '__main__':
    config.configure_logging()
    config.show_config()
    main()
