#from django.shortcuts import render
from django.http import HttpResponse
from models import User
from models import Activity
from forms import PhotoForm
import datetime
import json
import time
# Create your views here.


def register_new_user(request):
    out_data = {}
    errors = []
    if request.method == 'POST':
        user_name = request.POST.get('username', '')
        if not user_name:
            errors.append('user name post error')
        password_md5 = request.POST.get('password', '')
        if not password_md5:
            errors.append("password post error")

        if len(errors) > 0:
            out_data['ret'] = 'failed'
        else:
            if len(User.objects.filter(name = user_name)) == 0:
                out_data['ret'] = 'ok'
                reg_data = User(name = user_name, password = password_md5)
                reg_data.save()
                request.session['user'] = user_name
            else:
                out_data['ret'] = 'user_exist'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def login(request):
    errors = []
    out_data = {}
    if request.method == 'POST':
        user_name = request.POST.get('username', '')
        if not user_name:
            errors.append('user name post error')
        password_md5 = request.POST.get('password', '')
        if not password_md5:
            errors.append("password post error")
        if len(errors) > 0:
            out_data['ret'] = 'failed'
        login_usr = User.objects.filter(name = user_name)
        if len(login_usr) == 0:
            out_data['ret'] == 'user_not_exist'
        else:
            login_usr_obj = login_usr[0]
            if login_usr_obj.password == password_md5:
                request.session['user'] = user_name
                out_data['ret'] = 'ok'
            else:
                out_data['ret'] = 'password_error'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def logout(request):
    out_data = {}
    if request.method == "POST":
        if 'user' in request.session:
            user_name = request.session['user']
            del request.session['user']
            out_data['ret'] = 'ok'
        else:
            out_data['ret'] = "logout_session_failed"
    else:
        out_data["ret"] = "logout_failed"
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def push(request):
    out_data = {}

    return HttpResponse(json.dumps(out_data), content_type="application/json")

def get_account_info(request):
    out_data = {}
    if request.method == "GET":
        if 'user' in request.session:
            user_name = request.session['user']
            data = User.objects.get(name = user_name)
            out_data["ret"] = "ok"
            out_data["username"] = user_name
            out_data["location"] = data.location
            out_data["sex"] = data.sex
            out_data["score"] = str(data.score)
            out_data["completed"] = data.completed_id
            out_data["uncompleted"] = data.uncompleted_id
            out_data["userimage"] = str(data.img)
            out_data["label"] = data.label
        else:
            out_data["ret"] = "get_info_session_failed"
    else:
        out_data["ret"] = "get_info_failed"
    return HttpResponse(json.dumps(out_data), content_type="application/json")


def edit_sex(request):
    errors = []
    out_data = {}
    if request.method == 'POST':
        user_sex = request.POST.get('sex', '')
        if not user_sex:
            errors.append('edit_sex_error');
        if 'user' in request.session:
            user_name = request.session['user']
            data = User.objects.get(name = user_name)
            data.sex = user_sex
            data.save()
            out_data['ret'] = 'ok'
        else:
            out_data['ret'] = 'session_error'
        if len(errors) > 0:
            out_data['ret'] = 'post_error'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def edit_user_label(request):
    errors = []
    out_data = {}
    if request.method == 'POST':
        user_label = request.POST.get('label', '')
        if not user_label:
            errors.append('edit_label_error');
        if 'user' in request.session:
            user_name = request.session['user']
            data = User.objects.get(name = user_name)
            data.label = user_label
            data.save()
            out_data['ret'] = 'ok'
        else:
            out_data['ret'] = 'session_error'
        if len(errors) > 0:
            out_data['ret'] = 'post_error'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def upload_user_photo(request):
    out_data = {}
    if request.method=='POST':
        if 'user' in request.session:
            image = request.FILES["photo"]
            user_name = request.session['user']
            data = User.objects.get(name = user_name)
            data.img = image
            data.save()
            out_data['ret'] = 'ok'
        else:
            out_data['ret'] = 'session_failed'
    else:
        out_data['ret'] = 'post_failed'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def public_act(request):
    ret_data={}
    errors =[]
    if request.method=='POST':
        if 'user' in request.session:
            theme_act = request.POST.get('theme_act','')
            if not theme_act:
                errors.append('theme not exist')
            class_act  = request.POST.get('class_act','')
            date_act   = request.POST.get('date_act','')
            time_act   = request.POST.get('time_act','')
            num_act    = request.POST.get('num_act','')
            adress_act = request.POST.get('adress_act','')
            detail_act = request.POST.get('detail_act','')
            username = request.session['user']
            date_time = date_act + " " + time_act
            userid = User.objects.get(name=username).id
            now = datetime.datetime.fromtimestamp(time.mktime(time.
                    strptime(date_time, r"%Y/%m/%d %H:%M:%S")))
            datess = now.date().isoformat()
            if len(errors) > 0:
                ret_data['ret'] = 'no theme'
            else:
                pub_data = Activity(category = class_act, theme = theme_act,
                    begin_datatime = datess, people_count = num_act,
                    details = detail_act, submit_peopleId = userid)
                ret_data['ret'] = 'ok'
                pub_data.save()
        else:
            ret_data['ret'] = 'failed'
    else:
        ret_data['ret'] = 'failed'
    return HttpResponse(json.dumps(ret_data), content_type='application/json')

