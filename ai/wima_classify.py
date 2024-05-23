#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue May 14 18:27:10 2024

@author: ahmetbakcaci
"""

from flask import Flask, request, jsonify
import io
from model import ModelUtils
from PIL import Image
import torch
import base64
import numpy as np

app = Flask(__name__)

@app.route('/classify', methods=['POST'])
def classify():
    
    image_data = request.files['image'].read()
    
    image = Image.open(io.BytesIO(image_data))
    
    device = torch.device("cpu")

    weights_dir = './resnet18_4class_model_weights.pth'
    model_utils = ModelUtils(weights_dir, device)
    
    prediction, resized_img, cam_image = model_utils.predict_and_visualize(image)
    
    prediction = str(prediction)
    
    cam_pil_image = Image.fromarray(cam_image)
    resized_img = (resized_img * 255).astype(np.uint8)
    resized_pil_image = Image.fromarray(resized_img)

    buffer_1 = io.BytesIO()
    buffer_2 = io.BytesIO()
    cam_pil_image.save(buffer_1, format='PNG')
    resized_pil_image.save(buffer_2, format='PNG')

    buffer_1.seek(0)
    buffer_2.seek(0)
    
    encoded_cam_image = base64.b64encode(buffer_1.getvalue()).decode('utf-8')
    encoded_resized_image = base64.b64encode(buffer_2.getvalue()).decode('utf-8')
    
    return jsonify({'prediction': prediction, 'resizedImageData': encoded_resized_image, 'gradImageData': encoded_cam_image})

if __name__ == '__main__':
    app.run(host='172.20.10.5', port=8081)
