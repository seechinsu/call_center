#!/usr/bin/env python2

# process a sequence of json records for use by hive json serdes
# - change timestamp format from YYYY-MM-DDTHH:MM:SSZ to YYYY-MM-DD HH:MM:SS
# - add a dt partition field, which will be the first timestamp found in the record,
#   or if no timestamp is found, use today's date
# - replace IP addresses with 0.0.0.0

import re
import json
import fileinput
from collections import OrderedDict
from datetime import date

date_regex=r"(\d{4}-\d{2}-\d{2})T(\d{2}:\d{2}:\d{2}\.?\d*)Z"
date_re = re.compile(date_regex)

ip_regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}"
ip_re = re.compile(ip_regex)

matched_dt = None

# piggyback on the json parser traversal to alter fields
def hook(obj):
    global matched_dt  # sue me
    od = OrderedDict(obj)
    for key in od:

        # only process string fields
        if not isinstance(od[key], unicode):
            continue

        # replace timestamps
        m = date_re.match(str(od[key]))
        if m:
            od[key] = m.group(1) + u" " + m.group(2)
            if matched_dt is None:
                matched_dt = m.group(1)

        # redact IPs
        m = ip_re.match(str(od[key]))
        if m:
            od[key] = "0.0.0.0"

    return od


default_partition = unicode(date.today().isoformat())
for line in fileinput.input():
    matched_dt = None
    o = json.loads(line, object_pairs_hook=hook)
    o['dt'] = matched_dt if matched_dt is not None else default_partition
    print(json.dumps(o, separators=(',', ':')))
