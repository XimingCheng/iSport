#from django.shortcuts import render
from django.http import HttpResponse
from models import User
from forms import PhotoForm
import json

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

def push(request):
    out_data = {}

    return HttpResponse(json.dumps(out_data), content_type="application/json")

def edit_sex(request):
    errors = []
    out_data = {}
    if request.method == 'POST':
        user_sex = request.POST.get('sex', '')
        if not user_sex:
            errors.append('edit_sex_error');
        if 'user' in request.SESSION:
            user_name = request.session['User']
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
        pf = PhotoForm(request.POST, request.FILES)
        if pf.is_valid():
#            image = pf.photo
            image = request.FILES["photo"]
            user_name = 'testtest'
            data = User.objects.get(name = user_name)
            data.img = image
            data.save()
            out_data['ret'] = 'ok'
#            if 'user' in request.session['user']:
#                user_name = request.session['user']
#                data = User.objects.get(name = user_name)
#                data.img = pf.photo
#                data.save()
#                out_data['ret'] = 'ok'
#            else:
#                out_data['ret'] = 'session_error'
#        else:
#            out_data['ret'] = 'forms error'
    return HttpResponse(json.dumps(out_data), content_type="application/json")
