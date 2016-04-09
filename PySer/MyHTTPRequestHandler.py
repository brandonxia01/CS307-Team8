from http.server import BaseHTTPRequestHandler
from urllib.parse import parse_qs
from mySqlHandler import SqlHandler


class MyHTTPRequestHandler(BaseHTTPRequestHandler):
    sql = SqlHandler()

    def do_GET(self):
        tuples = self.performGetAction()
        response = ""
        for x in range(tuples.__len__()):
            for y in range(tuples[x].__len__()):
                response += str(tuples[x][y])
                response += ","
            response = response[:response.__len__() - 1]
            response += ";"
        response = response[:response.__len__() - 1]
        self.send_response(200)
        self.send_header("Content-type", "text/plain")
        self.end_headers()
        self.wfile.write(bytes(response, "utf-8"))

    def do_POST(self):
        content_len = int(self.headers.get('Content-Length', 0))
        post_body = self.rfile.read(content_len).decode("utf-8")
        result = parse_qs(post_body)
        for k in result:
            print(result[k])
        self.performPostAction(result)
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()
        self.wfile.write(bytes("sample response", "utf-8"))

    def performGetAction(self):
        reqType = int(self.headers['reqType'])
        argument = self.headers['argument']
        print('reqType', reqType, 'argument', argument)
        return self.sql.getTuple(reqType, argument)


    def performPostAction(self, bodyContent):
        reqType = self.headers['reqType']
        argument = self.headers['argument']
        return self.sql.performAction(reqType, argument, bodyContent)
