from flask import Flask, send_file
application = Flask(__name__)
#import gengraph

#    return "<h1 style='color:blue'>Hello There!</h1>"
@application.route("/")
def hello():
    return  """
            <html>
            <script type="text/javascript">
                  setTimeout(function () { location.reload(true); }, 2000);
            </script>
            <img src="/display?" + d.getTime() alt="alternative text" scale="0">
            </html>
            """

@application.route("/display")
def display():
    #gengraph.genimage2()
    return send_file("display.png", mimetype='image/png')

if __name__ == "__main__":
    application.run(host='0.0.0.0', port=8081)
