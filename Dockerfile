# Dùng Tomcat 10.1 (vì bạn dùng Jakarta Servlet 6.0)
FROM tomcat:10.1.24-jdk17-temurin

# Xóa ứng dụng mặc định của Tomcat (ROOT)
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy file WAR vào thư mục ứng dụng Tomcat và đặt tên là ROOT.war
COPY target/demo1_1-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Mở port 8080 (Render hoặc local sẽ truy cập qua cổng này)
EXPOSE 8080

# Chạy Tomcat khi container khởi động
CMD ["catalina.sh", "run"]
