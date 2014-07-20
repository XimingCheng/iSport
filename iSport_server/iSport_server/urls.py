from django.conf.urls import patterns, include, url
from django.contrib import admin
import settings
from iSport_server.iSport.views import register_new_user, login,upload_user_photo

admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'iSport_server.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),   # database admin
    url(r'^reg/', register_new_user),            # user register
    url(r'^login/', login),                       # user login
#    url(r'^push', push),                         # user push data
    url(r'^photo_upload/',upload_user_photo),   #user upload photo
    url(r'^media/(?P<path>.*)$', 'django.views.static.serve', {
                'document_root': settings.MEDIA_ROOT }),
)
