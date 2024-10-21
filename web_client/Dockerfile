FROM node:20.18.0-alpine

WORKDIR /app

COPY package.json .

COPY . .

EXPOSE 5173

CMD ["sh", "-c", "npm install && npm run dev"]
