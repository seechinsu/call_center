# wrapper to load test data and then spawn the migrator
import os
import config
import testdata

config.configure_logging()
testdata.load()
os.execlp("python3", "python3", "migrator.py")  # python3 twice is intentional
