#from django.shortcuts import render
from django.http import HttpResponse
from models import User
from models import Activity
from forms import PhotoForm
import datetime
import json
import time
import random
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

def getact(request):
    out_data = {}
    if request.method == "GET":
        if 'user' in request.session:
            user_name = request.session['user']
            out_data['act_id'] = request.GET.get('id', '')
            act_id = out_data['act_id']
            out_data['ret'] = 'ok'
            act = Activity.objects.get(id = act_id)
            out_data['time_act'] = str(act.begin_datatime)
            #print out_data['time_act']
            out_data['totalnum'] = act.people_count
            user_sub_id = str(act.submit_peopleId)
            out_data['theme_act'] = act.theme
            out_data['location_act'] = act.location
            out_data['detail_act'] = act.details
            splited_id = act.joined_peopleId.split(',')
            out_data['joined_num'] = "0"
            joined_num = 0
            if splited_id[0] != '':
                out_data['joined_num'] = str(len(splited_id))
                joined_num = int(out_data['joined_num'])
            out_data['bjoined'] = "no"
            if joined_num > 0:
                u = User.objects.get(name = user_name)
                uid = str(u.id)
                for id_str in splited_id:
                    if id_str == uid:
                        out_data['bjoined'] = 'yes'
                        break
            user = User.objects.get(id = user_sub_id)
            out_data['submit_img'] = str(user.img)
            out_data['img1'] = ''
            out_data['img2'] = ''
            out_data['img3'] = ''
            print splited_id, joined_num
            if joined_num >= 3:
                u = User.objects.get(id = splited_id[0])
                out_data['img1'] = str(u.img)
                u = User.objects.get(id = splited_id[1])
                out_data['img2'] = str(u.img)
                u = User.objects.get(id = splited_id[2])
                out_data['img3'] = str(u.img)
            elif joined_num == 2:
                u = User.objects.get(id = splited_id[0])
                out_data['img1'] = str(u.img)
                u = User.objects.get(id = splited_id[1])
                out_data['img2'] = str(u.img)
            elif joined_num == 1:
                out_data['img1'] = str(User.objects.get(id = splited_id[0]).img)
            else:
                pass
        else:
            out_data['ret'] = 'session_failed'
    else:
        out_data['ret'] = 'failed'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def getCompleted(request):
    out_data = {}
    if request.method == 'GET':
        out_data['ret'] = 'ok'
        user_name = request.GET.get("name", "")
        u = User.objects.get(name = user_name)
        splited_id = u.completed_id.split(',')
        if len(splited_id) > 0 and splited_id[0] != '':
            out_list = []
            count = 0
            for id_data in splited_id:
                if count > 2:
                    break;
                count += 1
                act = Activity.objects.get(id  = id_data)
                data = {}
                data['id'] = str(act.id)
                data['theme'] = act.theme
                data['details'] = act.details
                data['time'] = str(act.begin_datatime)
                data['count'] = str(act.people_count)
                u = User.objects.get(id = act.submit_peopleId)
                data['img'] = str(u.img)
                data['name'] = u.name
                out_list.append(data)
            out_data['list'] = out_list
            out_data['count'] = str(count)
    else:
        out_data['ret'] = 'failed'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def getUnCompleted(request):
    out_data = {}
    if request.method == 'GET':
        out_data['ret'] = 'ok'
        user_name = request.GET.get("name", "")
        u = User.objects.get(name = user_name)
        splited_id = u.uncompleted_id.split(',')
        if len(splited_id) > 0 and splited_id[0] != '':
            out_list = []
            count = 0
            for id_data in splited_id:
                if count > 2:
                    break;
                count += 1
                act = Activity.objects.get(id  = id_data)
                data = {}
                data['id'] = str(act.id)
                data['theme'] = act.theme
                data['details'] = act.details
                data['time'] = str(act.begin_datatime)
                data['count'] = str(act.people_count)
                u = User.objects.get(id = act.submit_peopleId)
                data['img'] = str(u.img)
                data['name'] = u.name
                out_list.append(data)
            out_data['list'] = out_list
            out_data['count'] = str(count)
    else:
        out_data['ret'] = 'failed'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def push(request):
    out_data = {}
    if request.method == "GET":
        all_act = Activity.objects.all()
        num_act = len(all_act)
        pushed_num = 10
        out_index = []
        for i in range(0, pushed_num):
            out_index.append(random.randint(0, num_act - 1))
        #print out_index
        out_data['ret'] = 'ok'
        out_list = []
        real_count = pushed_num
        for i in range(0, pushed_num):
            index = out_index[i]
            act = all_act[index]
            data = {}
            data['id'] = str(act.id)
            data['theme'] = act.theme
            data['details'] = act.details
            data['time'] = str(act.begin_datatime)
            data['count'] = str(act.people_count)
            #data.append(str(act.submit_peopleId))
            try:
                u = User.objects.get(id = act.submit_peopleId)
                data['img'] = str(u.img)
            except: #query user id not exist
                real_count -= 1
                continue
            data['name'] = u.name
            out_list.append(data)
        out_data['list'] = out_list
        out_data['count'] = real_count
    else:
        out_data['ret'] = 'failed'
    return HttpResponse(json.dumps(out_data), content_type="application/json")

def search(request):
    ret_data = {}
    if request.method == "GET":
        time_act=request.GET.get('time_act','')
        date_act=request.GET.get('date_act','')
   #     address_act=request.GET.get('address_act','')
        category_act=request.GET.get('class_act','')
        if time_act=='':
            time_date='1970-01-01 00:00:00'
        else:
            date_time = date_act + " " + time_act+":00"
            time_date = datetime.datetime.fromtimestamp(time.mktime(time.
                    strptime(date_time, r"%Y_%m_%d %H:%M:%S")))
        activity_items=Activity.objects.filter(category=category_act,begin_datatime__gte=time_date)
        num_act=len(activity_items)
        list=[]
        pagination=(10 if num_act>10 else num_act)
        ret_data['ret']='ok'
        for i in range(0,pagination):
            data={}
            activity_selected=activity_items[i]
            user=User.objects.get(id=activity_selected.submit_peopleId)
            data['name']=user.name
            data['img']=str(user.img)
            data['id']=activity_selected.id
            data['theme']=activity_selected.theme
            data['details']=activity_selected.details
            data['time']=str(activity_selected.begin_datatime)
            data['count']=activity_selected.people_count
    #        data['address']=activity_selected.location
            list.append(data)
        ret_data['list']=list
        ret_data['count']=num_act
    else:
        ret_data['ret']='failed'
    return HttpResponse(json.dumps(ret_data), content_type="application/json")

def reset_all_image(request):
    out_data = {}
    if request.method == "GET":
        #iimage = request.FILES["photo"]
        user_all = User.objects.all()
        num = len(user_all)
        for i in range(0, num):
            u = user_all[i]
            u.img = "photo/user_photo.png"
            u.save()
        out_data['ret'] = 'ok'
    else:
        out_data['ret'] = 'failed'
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
            out_data["ret"] = 'ok'
            user_name = request.GET.get("name", "")
            data = User.objects.get(name = user_name)
            out_data["username"] = user_name
            out_data["location"] = data.location
            out_data["sex"] = data.sex
            out_data["score"] = str(data.score)
            out_data["completed"] = data.completed_id
            out_data["uncompleted"] = data.uncompleted_id
            out_data["userimage"] = str(data.img)
            out_data["label"] = data.label

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

