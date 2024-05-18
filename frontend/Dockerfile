FROM node:21.7.1 AS builder


WORKDIR /app
COPY package.json pnpm-lock.yaml ./
RUN npm install -g pnpm
RUN pnpm install

COPY . .
RUN pnpm run build
#EXPOSE 5173
#CMD ["pnpm", "run", "dev"]

FROM nginx:latest
RUN rm /etc/nginx/conf.d/default.conf
RUN rm -rf /etc/nginx/conf.d/*
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf

COPY --from=builder app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]