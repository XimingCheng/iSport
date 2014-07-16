#from django.shortcuts import render
from django.http import HttpResponse
from models import User, Activity

# Create your views here.

def register_new_user(request):
    errors = []
    if request.method == 'POST':
        user_name = request.POST.get('username', '')
        if not user_name:
            errors.append('user name post error')
        password_md5 = request.POST.get('password', '')
        if not password_md5:
            errors.append("password post error")

    out_data = {}
    if len(errors) > 0:
        out_data['ret'] = 'failed'
    else:
        if len(User.objects.filter(name = user_name)) == 0:
            out_data['ret'] = 'ok'
            reg_data = User(name = user_name, password = password_md5)
            reg_data.save()
        else:
            out_data['ret'] = 'user_exist'
    return HttpResponse(json.dump(out_data), content_type="application/json")

def login(request):
    errors = []
    if request.method == 'POST':
        user_name = request.POST.get('username', '')
        if not user_name:
            errors.append('user name post error')
        password_md5 = request.POST.get('password', '')
        if not password_md5:
            errors.append("password post error")
    out_data = []
    if len(out_data) > 0:
        out_data['ret'] = 'failed'
    login_usr = User.objects.filter(name = user_name)
    if len(login_usr) == 0:
        out_data['ret'] == 'user_not_exist'
    else:
        login_usr_obj = login_usr[0]
        if login_usr_obj.password == password_md5:
            out_data['ret'] = 'ok'
        else:
            out_data['ret'] = 'password_error'
    return HttpResponse()

