import requests


def authentication():
    authenticate_url = "https://api.artsy.net/api/tokens/xapp_token"
    client_id = '09098df81d60702eb326'
    client_secret = '1468cf45cb6f660d9266d3d550737337'
    data = "client_id=%s&client_secret=%s" % (client_id, client_secret)
    headers = {"Content-Type": "application/x-www-form-urlencoded"}
    token = requests.post(authenticate_url, headers=headers, data=data).json()
    return token


def get_token(authentication_token):
    x_xapp_token = authentication_token["token"]
    return x_xapp_token


def process_search(search_list):
    extract_list = list()
    for node in search_list["_embedded"]["results"]:
        if node["og_type"] == "artist":
            artist_name = node["title"]
            artist_id = node["_links"]["self"]["href"][34:]
            artist_pic = node["_links"]["thumbnail"]["href"]
            curr_node = dict()
            curr_node.update({"name": artist_name,
                              "ID": artist_id,
                              "image": artist_pic})
            extract_list.append(curr_node)
    return extract_list


def search_endpoint(input_name):
    xapp_token = get_token(authentication())
    search_url = "https://api.artsy.net/api/search?q=%s&size=10" % input_name
    headers = {'X-XAPP-Token': xapp_token}
    filter_artist = requests.get(search_url, headers=headers).json()
    try:
        filtered_list = process_search(filter_artist)
        return filtered_list
    except KeyError:
        return []


def process_artist(artist_info):
    return {"name": artist_info["name"],
            "birthday": artist_info["birthday"],
            "deathday": artist_info["deathday"],
            "nationality": artist_info["nationality"],
            "biography": artist_info["biography"]}


def artist_detail(id_num):
    xapp_token = get_token(authentication())
    artist_url = "https://api.artsy.net/api/artists/%s" % id_num
    headers = {'X-XAPP-Token': xapp_token}
    info_artist = requests.get(artist_url, headers=headers).json()
    try:
        filtered_info = process_artist(info_artist)
        return filtered_info
    except KeyError:
        return{}
