from django.conf.urls import patterns, include, url
from django.contrib import admin
import settings
from iSport_server.iSport.views import register_new_user, login
from iSport_server.iSport.views import upload_user_photo, get_account_info
from iSport_server.iSport.views import logout,public_act, edit_sex, edit_user_label

admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'iSport_server.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),   # database admin
    url(r'^reg/', register_new_user),            # user register
    url(r'^login/', login),                       # user login
#    url(r'^push', push),                         # user push data
    url(r'^photo_upload/', upload_user_photo),   #user upload photo
    url(r'^media/(?P<path>.*)$', 'django.views.static.serve', {
                'document_root': settings.MEDIA_ROOT }),
    url(r'^getinfo/', get_account_info),
    url(r'^logout/', logout),
    url(r'^public/', public_act),
    url(r'^edit_sex/', edit_sex),
    url(r'^edit_label/', edit_user_label),
)


