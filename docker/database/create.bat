docker build -t autohub-postgres .

docker run -d --name autohub-postgres -p 5432:5432 -e POSTGRES_DB=autohub -e POSTGRES_USER=autohub_user -e POSTGRES_PASSWORD=autohub_pass autohub-postgres
