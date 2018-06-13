import collections
import csv
import logging
import os
import re

import boto3
import botocore.exceptions

import config

logger = logging.getLogger(__name__)

Header = collections.namedtuple('Header', ['name', 'processor'])


def process_headers(row):
    processors = {'S': str, 'N': int, 'B': str}
    for item in row:
        m = re.match("""^(\w+) \(([SNB])\)$""", item)
        if m:
            yield Header(m.group(1), processors[m.group(2)])


def get_table():
    dynamodb = boto3.resource('dynamodb', endpoint_url=config.DYNAMODB_ENDPOINT, region_name=config.AWS_REGION)
    table = dynamodb.create_table(
        TableName=config.DYNAMODB_TABLE,
        KeySchema=[
            {
                'AttributeName': 'id',
                'KeyType': 'HASH'
            }
        ],
        AttributeDefinitions=[
            {
                'AttributeName': 'id',
                'AttributeType': 'S'
            }
        ],
        ProvisionedThroughput={
            'ReadCapacityUnits': 5,
            'WriteCapacityUnits': 5
        }
    )
    table.meta.client.get_waiter('table_exists').wait(TableName=config.DYNAMODB_TABLE)
    return table


def load():
    logger.info("loading test data")
    try:
        table = get_table()
    except botocore.exceptions.ClientError:
        logger.warn("table already created, abandoning load")
        return

    datafile = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'institution.csv')
    with open(datafile, 'r') as csvfile:
        reader = csv.reader(csvfile)
        for idx, row in enumerate(reader):
            if idx == 0:
                headers = list(process_headers(row))
                continue
            upload_map = {}
            for (header, item) in zip(headers, row):
                if item:
                    upload_map[header.name] = header.processor(item)
            table.put_item(Item=upload_map)

