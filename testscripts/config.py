import os
import logging

# configure these
DYNAMODB_ENDPOINT = os.getenv('DYNAMODB_ENDPOINT', None)
DYNAMODB_TABLE = os.getenv('DYNAMODB_TABLE', 'institution')
BROADCAST_HOST = os.getenv('BROADCAST_HOST', None)
BROADCAST_PATH = os.getenv('BROADCAST_PATH', '/inject')
AWS_REGION = os.getenv('AWS_REGION', None)
LOG_LEVEL = os.getenv('LOG_LEVEL', 'INFO')


def configure_logging():
    logging.basicConfig(level=LOG_LEVEL)


def show_config():
    for key, value in globals().items():
        if key and key[0].isupper():
            logging.info("{}={}".format(key, value))
