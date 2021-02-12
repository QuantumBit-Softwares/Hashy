################################################
# PIP - Python Package Manager to install modules
import pip

def install(package):
    if hasattr(pip, 'main'):
        pip.main(['install', package])
    else:
        pip._internal.main(['install', package])

# Installing modules
if __name__ == '__main__':
    install('matplotlib')
    install('twitter')
    install('tweepy')
    install('nltk')
    install('textblob')
    install('cherrypicker')
    install('pandas')
    install('prettytable')
