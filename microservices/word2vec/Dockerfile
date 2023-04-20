# Use an official Python runtime as a parent image
FROM python:3.8

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Expose the port the Flask app will be listening on
EXPOSE 5000

# Set the environment variable for Flask app
ENV FLASK_APP app.py

# Set the command to start the Flask app
CMD ["flask", "run", "--host=0.0.0.0"]
