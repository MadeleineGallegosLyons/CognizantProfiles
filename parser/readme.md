In the CognizantProfiles directory, run this command to open a virtual environment.
py -m venv pymupdf-venv
.\pymupdf-venv\Scripts\activate
python -m pip install --upgrade pip

cd into parser
run this command to install pymupdf:
pip install --upgrade pymupdf

run this command to install azure.storage.blob:
pip install azure-storage-blob azure-identity

To run parser: 
python parsePDF.py
>> copy paste your profile's path