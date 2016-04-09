from http.client import HTTPConnection
import urllib.parse


def sendTestGET():
    conn = HTTPConnection('ec2-52-38-208-27.us-west-2.compute.amazonaws.com', 9000)
    # method url body header
    conn.request('GET', '', None, {'reqType' : '5', 'argument' : 'ayushVijayjayjay'})

    res = conn.getresponse()

    print(res.status, res.reason, res.getheaders())
    print(res.read(res.length))


def sendTestPOST():
    # conn = HTTPConnection('localhost', 9000)
    conn = HTTPConnection('ec2-52-38-208-27.us-west-2.compute.amazonaws.com', 9000)
    # method url body header
    params = urllib.parse.urlencode({'followingName': 'ayishVijiwirgi', 'followerName': 'AyeshEatsEggs'})
    conn.request('POST', '', params, {'reqType' : 4})

    res = conn.getresponse()

    print(res.status, res.reason, res.getheaders())
    print(res.read(res.length))

sendTestGET()
