FROM python:3.12.3-slim-bookworm
WORKDIR /code
ENV FLASK_APP=wima_classify.py
ENV FLASK_RUN_HOST=0.0.0.0
RUN apt-get update && apt-get install ffmpeg libsm6 libxext6  -y
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
EXPOSE 5000
COPY . .
COPY resnet18-f37072fd.pth /root/.cache/torch/hub/checkpoints/resnet18-f37072fd.pth
CMD ["flask", "run", "--debug"]
