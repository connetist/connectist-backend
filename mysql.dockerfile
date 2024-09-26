# Use the official MySQL 8.0 image as the base
FROM mysql:8.0

# Set environment variables for MySQL
ENV MYSQL_ROOT_PASSWORD=password1234
ENV MYSQL_DATABASE=connectist-db
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=1234

# Expose the default MySQL port (3306)
EXPOSE 3306

# Start MySQL server
CMD ["mysqld"]


#docker build -t custom-mysql -f mysql_custom.dockerfile .
#docker run -d --name mysql-container -p 3307:3306 custom-mysql