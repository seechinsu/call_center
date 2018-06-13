FROM python:3.5

RUN apt-get update
RUN apt-get install nano
RUN echo "set tabsize 4" > ~/.nanorc && echo "set tabstospaces" >> ~/.nanorc

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY requirements.txt /usr/src/app/
RUN pip install --no-cache-dir -r requirements.txt
COPY . /usr/src/app

CMD ["python3", "/usr/src/app/web.py", ">", "/usr/src/app/requests.log"]
