# Use the official MongoDB image from Docker Hub
FROM mongo:6.0

# Set environment variables (optional)
ENV MONGO_INITDB_ROOT_USERNAME=user
ENV MONGO_INITDB_ROOT_PASSWORD=1234

# Expose the internal MongoDB port
EXPOSE 27017

# Command to run MongoDB
CMD ["mongod"]

#docker build -t custom-mongo -f mongo_custom.dockerfile .
#docker run -d --name mongodb-container -p 27108:27017 custom-mongo